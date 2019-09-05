package com.jianghu.winter.query.core;

import com.alibaba.fastjson.JSON;
import com.jianghu.winter.query.user.UserEntity;
import com.jianghu.winter.query.user.UserMapper;
import com.jianghu.winter.query.user.UserQuery;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.*;

import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * @author daniel.hu
 * @date 2019/8/27 10:28
 */
@Slf4j
public class MybatisTest {

    private static SqlSessionFactory sqlSessionFactory;
    private SqlSession sqlSession;
    private UserMapper userMapper;
    private static Connection connection;

    @BeforeClass
    public static void init() throws Exception {
        Reader reader = Resources.getResourceAsReader("mybatis-config.xml");
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(reader);
        connection = sqlSessionFactory.openSession().getConnection();
    }

    @AfterClass
    public static void closeConn() throws SQLException {
        connection.close();
    }

    @Before
    public void setUp() throws Exception {
        new ScriptRunner(connection).runScript(Resources.getResourceAsReader("import.sql"));
        sqlSession = sqlSessionFactory.openSession();
        userMapper = sqlSession.getMapper(UserMapper.class);
    }

    @After
    public void tearDown() {
        sqlSession.close();
    }

    @Test
    public void test_select() {
        List<UserEntity> entities = userMapper.query(UserQuery.builder().build());
        log.info("query response:\n{}", JSON.toJSONString(entities, true));
        assertEquals(4, entities.size());
    }

    @Test
    public void test_count() {
        UserQuery userQuery = UserQuery.builder().build();
        userQuery.setPageNumber(0);
        userQuery.setPageSize(2);
        userQuery.setSort("account,asc");
        assertEquals(4, userMapper.count(userQuery));
    }


    @Test
    public void test_page() {
        UserQuery query = UserQuery.builder().build();
        query.setPageNumber(0);
        query.setPageSize(2);
        PageList<UserEntity> page = userMapper.page(query);
        log.info("page response:\n{}", JSON.toJSONString(page, true));
        assertEquals(4, page.total);
        assertEquals(2, page.getList().size());
    }

    @Test
    public void test_where() {
        List<UserEntity> entities = userMapper.query(UserQuery.builder().account("daniel").build());
        assertEquals(1, entities.size());
    }

    @Test
    public void test_getById() {
        UserEntity userEntity = userMapper.get(1);
        log.info("entity:\n{}", JSON.toJSONString(userEntity, true));
        assertEquals("daniel", userEntity.getAccount());
    }

    @Test
    public void test_delete() {
        userMapper.delete(1);
        assertNull(userMapper.get(1));
    }

    @Test
    public void test_deleteByQuery() {
        userMapper.deleteByQuery(UserQuery.builder().account("daniel").build());
        assertNull(userMapper.get(1));
    }

    @Test
    public void test_getByQuery() {
        UserEntity entity = userMapper.getByQuery(UserQuery.builder().userNameLike("张").build());
        assertEquals("user2", entity.getAccount());
    }

    @Test
    public void test_in() {
        List<UserEntity> entities = userMapper.query(UserQuery.builder().idIn(Arrays.asList(1, 2)).build());
        assertEquals(2, entities.size());
    }

    @Test
    public void test_insert() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAccount("test");
        userEntity.setEmail("123456@qq.com");
        userEntity.setNickName("James");
        userEntity.setMobile("18312341234");
        userEntity.setPassword("123456");
        userEntity.setValid(true);

        userMapper.insert(userEntity);
        UserEntity entity = userMapper.getByQuery(UserQuery.builder().account("test").build());
        log.info("insert entity:\n{}", JSON.toJSONString(entity, true));
        assertThat(entity)
                .hasFieldOrPropertyWithValue("id", 5)
                .hasFieldOrPropertyWithValue("account", "test")
                .hasFieldOrPropertyWithValue("email", "123456@qq.com")
                .hasFieldOrPropertyWithValue("mobile", "18312341234")
                .hasFieldOrPropertyWithValue("nickName", "James")
                .hasFieldOrPropertyWithValue("password", "123456")
                .hasFieldOrPropertyWithValue("valid", true)

        ;
    }

    @Test
    public void test_batchInsert() {
        List<UserEntity> list = new ArrayList<>();

        UserEntity user1 = new UserEntity();
        user1.setAccount("test1");
        user1.setEmail("123456@qq.com");
        user1.setMobile("18312341234");
        user1.setNickName("James");
        user1.setPassword("123456");
        user1.setValid(true);
        list.add(user1);

        UserEntity user2 = new UserEntity();
        user2.setAccount("test2");
        user2.setEmail("123456@qq.com");
        user2.setMobile("18312341234");
        user2.setNickName("James");
        user2.setPassword("123456");
        user2.setValid(true);
        list.add(user2);

        UserQuery userQuery = UserQuery.builder().build();
        long count = userMapper.count(userQuery);
        int insertCount = userMapper.batchInsert(list);
        assertEquals(2, insertCount);
        List<UserEntity> entities = userMapper.query(userQuery);
        log.info("insert statement:\n{}", JSON.toJSONString(entities, true));
        assertEquals(count + 2, userMapper.count(userQuery));

    }

    @Test
    public void test_update() {
        UserEntity userEntity = userMapper.get(1);
        userEntity.setMobile(null);
        userEntity.setUserName("updateName");
        userMapper.update(userEntity);
        UserEntity afterUpdate = userMapper.get(1);
        log.info("afterUpdate:\n{}", JSON.toJSONString(afterUpdate, true));
        assertThat(afterUpdate)
                .hasFieldOrPropertyWithValue("mobile", null)
                .hasFieldOrPropertyWithValue("userName", "updateName");
    }

    @Test
    public void test_patch() {
        UserEntity userEntity = userMapper.get(1);
        userEntity.setMobile(null);
        userEntity.setUserName("updateName");
        userMapper.patch(userEntity);
        UserEntity afterUpdate = userMapper.get(1);
        log.info("afterUpdate:\n{}", JSON.toJSONString(afterUpdate, true));
        assertThat(afterUpdate)
                .hasFieldOrProperty("mobile").isNotNull()
                .hasFieldOrPropertyWithValue("userName", "updateName");
    }

    @Test
    public void test_patchByQuery() {
        UserEntity userEntity = new UserEntity();
        userEntity.setValid(false);

        UserQuery userQuery = UserQuery.builder().account("daniel").build();
        int updateCount = userMapper.patchByQuery(userEntity, userQuery);
        assertEquals(1, updateCount);
        UserEntity afterUpdate = userMapper.getByQuery(userQuery);
        assertThat(afterUpdate)
                .hasFieldOrPropertyWithValue("valid", false);
    }
}
