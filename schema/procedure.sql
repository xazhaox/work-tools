/**
	创建表模型，数据类型为varchar形式，数据大小字段为具体大小
 */
DROP PROCEDURE IF EXISTS table_model_column;
DELIMITER //

CREATE PROCEDURE table_model_column (
    IN database_name VARCHAR(255),
    IN target_table VARCHAR(255)
)
BEGIN
    SELECT
        column_name AS columnName,
        column_comment AS columnComment,
        data_type AS dataType,
        character_maximum_length AS characterMaximumLength,
        CASE WHEN column_key = 'PRI' THEN 'YES' ELSE 'NO' END AS isColumnKey,
        is_nullable as isNullable
    FROM
        information_schema.columns
    WHERE
            table_schema = database_name
      AND table_name = target_table
    ORDER BY ordinal_position ASC;
END //

DELIMITER ;

/**
	创建表模型，数据类型为varchar(255)形式，无数据大小字段
 */
DROP PROCEDURE IF EXISTS table_model_is_not_data_type;
DELIMITER //

CREATE PROCEDURE table_model_is_not_data_type (
    IN database_name VARCHAR(255),
    IN target_table VARCHAR(255)
)
BEGIN
    SELECT
        column_name AS columnName,
        column_comment AS columnComment,
        column_type AS columnType,
        CASE WHEN column_key = 'PRI' THEN 'YES' ELSE 'NO' END AS isColumnKey,
        is_nullable as isNullable
    FROM
        information_schema.columns
    WHERE
            table_schema = database_name
      AND table_name = target_table
    ORDER BY ordinal_position ASC;
END //

DELIMITER ;
