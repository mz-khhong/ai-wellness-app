package com.aiwellness.common.persistence.typehandler;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * com.aiwellness.common.persistence.typehandler
 * <p>
 * EnumTypeHandler
 * <p>
 * MyBatis에서 Enum 타입을 자동으로 매핑하는 범용 TypeHandler
 * <p>
 * 데이터베이스의 문자열 값을 Java Enum의 name()으로 변환합니다.
 * <p>
 * 사용 방법:
 * <pre>
 * {@code
 * <result property="status" column="status" 
 *         typeHandler="com.aiwellness.common.persistence.typehandler.EnumTypeHandler"/>
 * }
 * </pre>
 *
 * @author 메가존 시스템
 * @version 1.0
 * @since 2025. 11. 17.
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *     수정일        수정자           수정내용
 *  ----------    --------        ---------------------------
 *  2025. 11. 17.    메가존 시스템            최초 생성
 * </pre>
 */
public class EnumTypeHandler<E extends Enum<E>> extends BaseTypeHandler<E> {

    private final Class<E> type;

    @SuppressWarnings("unchecked")
    public EnumTypeHandler(Class<?> type) {
        if (type == null) {
            throw new IllegalArgumentException("Type argument cannot be null");
        }
        this.type = (Class<E>) type;
    }

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, E parameter, JdbcType jdbcType) throws SQLException {
        // Enum의 name()을 문자열로 저장 (예: "ACTIVE", "INACTIVE")
        ps.setString(i, parameter.name());
    }

    @Override
    public E getNullableResult(ResultSet rs, String columnName) throws SQLException {
        String value = rs.getString(columnName);
        return value == null ? null : Enum.valueOf(type, value);
    }

    @Override
    public E getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        String value = rs.getString(columnIndex);
        return value == null ? null : Enum.valueOf(type, value);
    }

    @Override
    public E getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        String value = cs.getString(columnIndex);
        return value == null ? null : Enum.valueOf(type, value);
    }
}

