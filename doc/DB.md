## WeightLoss 数据库创建脚本
```sql
-- 创建数据库
CREATE DATABASE IF NOT EXISTS weight-loss 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

-- 使用数据库
USE weight-loss;

-- 创建用户表
CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '用户ID',
    username VARCHAR(50) NOT NULL UNIQUE COMMENT '用户名',
    password VARCHAR(100) NOT NULL COMMENT '加密后的密码',
    enabled TINYINT(1) NOT NULL DEFAULT 1 COMMENT '是否启用',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_username (username)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表';

-- 创建测试用户 密码：123456
INSERT INTO users (username, password) VALUES
('admin', '$2a$10$rOzZUVb7kCdUQp3qJ5x4E.4bQ4QZ8QjLpLkLmNpQrStUvWwXyZzAb' ),
('testuser', '$2a$10$rOzZUVb7kCdUQp3qJ5x4E.4bQ4QZ8QjLpLkLmNpQrStUvWwXyZzAb');

-- 创建用户设置表
CREATE TABLE user_settings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '设置ID',
    user_id BIGINT NOT NULL UNIQUE COMMENT '用户ID',
    height DECIMAL(5,2) DEFAULT NULL COMMENT '身高(cm)',
    target_weight DECIMAL(5,2) DEFAULT NULL COMMENT '目标体重(kg)',
    weight_unit VARCHAR(10) NOT NULL DEFAULT 'KG' COMMENT '体重单位: KG, JIN',
    reminder_enabled TINYINT(1) NOT NULL DEFAULT 0 COMMENT '是否开启提醒',
    reminder_time VARCHAR(5) DEFAULT NULL COMMENT '提醒时间 HH:mm',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    INDEX idx_user_id (user_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户设置表';

-- 创建体重记录表
CREATE TABLE weight_records (
    id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '记录ID',
    user_id BIGINT NOT NULL COMMENT '用户ID',
    record_date DATE NOT NULL COMMENT '记录日期',
    weight DECIMAL(5,2) NOT NULL COMMENT '体重(kg)',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    UNIQUE KEY uk_user_date (user_id, record_date),
    INDEX idx_user_id (user_id),
    INDEX idx_record_date (record_date),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='体重记录表';
```