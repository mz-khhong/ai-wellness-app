package com.aiwellness.common.persistence.interceptor;

import java.util.Arrays;
import java.util.Optional;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.session.Configuration;
import org.springframework.util.StringUtils;

/**
 * com.aiwellness.common.persistence.interceptor
 * <p>
 * QueryIdGenerationBoundSql
 * <p>
 * BoundSql을 확장하여 SQL 쿼리에 QueryId 주석을 추가하는 클래스
 * <p>
 * MapperQueryId 어노테이션을 통해 클래스 또는 메서드 레벨에서
 * QueryId의 prefix/suffix를 커스터마이징할 수 있습니다.
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 19.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 19.    메가존 시스템            최초 생성
 * </pre>
 */
class QueryIdGenerationBoundSql extends BoundSql {

    private static final String ID_DELIMITER = ".";

    private final BoundSql boundSql;
    private final String baseQueryId;
    private final String defaultQueryIdPrefix;

    private String[] baseQueryIdParts;

    public QueryIdGenerationBoundSql(
        Configuration config,
        BoundSql boundSql,
        String baseQueryId,
        String defaultQueryIdPrefix
    ) {
        super(config, boundSql.getSql(), boundSql.getParameterMappings(), boundSql.getParameterObject());
        this.boundSql = boundSql;
        this.baseQueryId = baseQueryId; //기본 쿼리ID: "{FQCN}.{methodName}"
        this.defaultQueryIdPrefix = defaultQueryIdPrefix;
    }

    @Override
    public String getSql() {
        return generateQueryIdComment() + boundSql.getSql();
    }

    private String generateQueryIdComment() {
        return "/* " + generateQueryId() + " */" + System.lineSeparator();
    }

    private String generateQueryId() {
        baseQueryIdParts = splitBaseQueryId();
        QueryIdDecorator queryIdDecorator = getQueryIdDecorator();
        if (baseQueryIdParts.length < 2) {
            return queryIdDecorator.concat(baseQueryId);
        }
        return queryIdDecorator.concat(String.join(ID_DELIMITER, getClassNameOnly(), getMethodNameOnly()));
    }

    private String[] splitBaseQueryId() {
        return baseQueryId.split("\\.");
    }

    private QueryIdDecorator getQueryIdDecorator() {
        Class<?> clazz;
        try {
            String className = getPackageClassNameOnly();
            clazz = Class.forName(className);
        } catch (Exception e) {
            return QueryIdDecorator.of(defaultQueryIdPrefix, "");
        }

        String methodName = getMethodNameOnly();
        Optional<QueryIdDecorator> methodAddingQueryId = getAddingQueryIdOfMethod(clazz, methodName);
        if (methodAddingQueryId.isPresent()) {
            return methodAddingQueryId.get();
        }

        Optional<QueryIdDecorator> classAddingQueryId = getAddingQueryIdOfClass(clazz);
        return classAddingQueryId.orElseGet(() -> QueryIdDecorator.of(defaultQueryIdPrefix, ""));
    }

    private String getClassNameOnly() {
        return baseQueryIdParts[baseQueryIdParts.length - 2];
    }

    private String getMethodNameOnly() {
        return baseQueryIdParts[baseQueryIdParts.length - 1];
    }

    private String getPackageClassNameOnly() {
        return String.join(ID_DELIMITER, Arrays.copyOf(baseQueryIdParts, baseQueryIdParts.length - 1));
    }

    private Optional<QueryIdDecorator> getAddingQueryIdOfMethod(Class<?> clazz, String methodName) {
        return Arrays.stream(clazz.getDeclaredMethods())
            .filter(method -> method.getName().equals(methodName))
            .findAny()
            .filter(method -> method.getAnnotation(MapperQueryId.class) != null)
            .map(method -> QueryIdDecorator.of(method.getAnnotation(MapperQueryId.class)));
    }

    private Optional<QueryIdDecorator> getAddingQueryIdOfClass(Class<?> clazz) {
        MapperQueryId classAnnotation = clazz.getAnnotation(MapperQueryId.class);
        if (classAnnotation != null) {
            return Optional.of(QueryIdDecorator.of(classAnnotation));
        }
        return Optional.empty();
    }


    record QueryIdDecorator(String prefix, String suffix) {

        public static QueryIdDecorator of(MapperQueryId annotation) {
            return of(annotation.prefix(), annotation.suffix());
        }

        public static QueryIdDecorator of(String prefix, String suffix) {
            String realPrefix = StringUtils.hasText(prefix) ? prefix + ID_DELIMITER : "";
            String realSuffix = StringUtils.hasText(suffix) ? ID_DELIMITER + suffix : "";
            return new QueryIdDecorator(realPrefix, realSuffix);
        }

        public String concat(String baseId) {
            return prefix + baseId + suffix;
        }
    }
}
