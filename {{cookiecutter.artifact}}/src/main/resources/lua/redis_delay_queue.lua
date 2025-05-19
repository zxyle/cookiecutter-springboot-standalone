-- redis延时队列Lua脚本
-- 版本号：v1.0.0
-- 参数说明：
-- KEYS[1] 是延时队列的 key 名称，比如 "delay_queue"
-- ARGV[1] 是当前时间戳（秒）
-- ARGV[2] 是本次最多获取的消息数量（batchSize）

local key = KEYS[1]
local max_score = tonumber(ARGV[1])
local batch_size = tonumber(ARGV[2])

-- 获取 score <= max_score 的前 batch_size 条消息
local messages = redis.call('ZRANGEBYSCORE', key, 0, max_score, 'LIMIT', 0, batch_size)

-- 如果有消息，则一次性批量删除
if #messages > 0 then
    -- 构造参数列表：{'ZREM', 'key', 'msg1', 'msg2', ...}
    local args = { key }
    for _, member in ipairs(messages) do
        table.insert(args, member)
    end

    -- 使用 unpack 展开参数列表进行批量删除
    redis.call('ZREM', unpack(args))
end

return messages