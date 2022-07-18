local current

current = redis.call("incr",KEYS[1])

if tonumber(current) == 1 then

    redis.call("expire",KEYS[1],1)
    return current
end
    return current