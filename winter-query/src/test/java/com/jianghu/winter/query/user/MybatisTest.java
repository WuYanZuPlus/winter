package com.jianghu.winter.query.user;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.Reader;
import java.sql.Connection;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author daniel.hu
 * @date 2019/8/27 10:28
 */
@Slf4j
public class MybatisTest {

    private static SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private UserMapper userMapper;

    @BeforeClass
    public static void init() throws Exception {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        Connection connection = sqlSessionFactory.openSession().getConnection();
        new ScriptRunner(connection).runScript(Resources.getResourceAsReader("import.sql"));
    }

    @Before
    public void setUp() throws Exception {
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void tearDown() throws Exception {
        sqlSession.close();
    }

    @Test
    public void name() {
        List<UserEntity> entities = userMapper.query(UserQuery.builder().build());
        log.debug("query response:\n{}", JSON.toJSONString(entities, true));
        assertEquals(4, entities.size());

    }
}
