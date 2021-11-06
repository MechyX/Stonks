package Database;

import redis.clients.jedis.Jedis;

public class RedisAdapter {

    public Jedis jedis;

    public void connect () {
        try {
            jedis = new Jedis("localhost");
            System.out.println("Connected");
        }catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
