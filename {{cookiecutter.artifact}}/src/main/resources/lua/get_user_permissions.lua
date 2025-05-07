-- 参数说明：
-- KEYS[1]: 用户ID（例如 "123"）
-- 返回值：用户所有权限的列表（例如 ["read", "write"]）

-- 1. 构造用户角色集合的Key
local user_roles_key = "user:" .. KEYS[1] .. ":roles"

-- 2. 获取用户所有角色
local roles = redis.call('SMEMBERS', user_roles_key)
if #roles == 0 then
    return {}  -- 无角色返回空列表
end

-- 3. 构造所有角色权限的Key列表
local role_permission_keys = {}
for _, role in ipairs(roles) do
    table.insert(role_permission_keys, "role:" .. role .. ":permissions")
end

-- 4. 合并所有角色的权限集合并返回
return redis.call('SUNION', unpack(role_permission_keys))