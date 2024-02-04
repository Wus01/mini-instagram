package instagram.miniinstagram.repository;

import instagram.miniinstagram.domain.User;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.*;

import static java.sql.DriverManager.getConnection;
import static org.apache.tomcat.util.descriptor.InputSourceUtil.close;


public class JdbcUserRepository implements UserRepository {
    /*
    private static long sequence = 0L;
    private static Map<Long, User> store= new HashMap<>();

     */
    private final DataSource dataSource;        //db 접근

    @Autowired
    public JdbcUserRepository(DataSource dataSource){
        this.dataSource = dataSource;
    }



    //데이터 저장
    @Override
    public User save(User user) {
        /*
        user.setId(++sequence);             //id 값 설정
        store.put(user.getId(), user);
         */

        String sql = "insert into member(email, name, nic_name, password) values(?,?,?,?)";
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString (1, user.getEmail());
            pstmt.setString(2, user.getName());
            pstmt.setString(3, user.getNic_name());
            pstmt.setString(4, user.getPassword());
/*
            pstmt.setString(5, user.getProfile_img());
            pstmt.setString(6, user.getWebSite());
            pstmt.setString(7, user.getIntroduction());
*/

            pstmt.executeUpdate();                      //쿼리 전달

            rs = pstmt.getGeneratedKeys();


            if(rs.next()){
                user.setId(rs.getLong(1));


            }else {
                throw new SQLException("id 조회 실패");

            }
            return user;

        }catch (Exception e){
            throw new IllegalStateException(e);


        }finally {
            close(conn, pstmt, rs);
        }

    }

    //데이터 조회
    @Override
    public Optional<User> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try{
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);

            rs = pstmt.executeQuery();

            if(rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                return Optional.of(user);
            }else{
                return Optional.empty();
            }

        }catch (SQLException e){
            throw new IllegalStateException(e);

        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        String sql = "select * from member where email = ?";


        return Optional.empty();
    }

    @Override
    public Optional<User> findByName(String name) {
        String sql = "select * from member where name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);

            rs = pstmt.executeQuery();

            List<User> users = new ArrayList<>();

            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                return Optional.of(user);
            }
            return Optional.empty();

        }catch (SQLException e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);

        }
    }

    @Override
    public Optional<User> findByPassword(String password) {
        return Optional.empty();

    }

    @Override
    public Optional<User> findByUser(User user) {
        String sql = "select * from member where email = ? and password = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            pstmt.setString (1, user.getEmail());
            pstmt.setString(2, user.getPassword());
/*
            pstmt.setString(5, user.getProfile_img());
            pstmt.setString(6, user.getWebSite());
            pstmt.setString(7, user.getIntroduction());
*/

                                 //쿼리 전달
            rs = pstmt.executeQuery();
            //rs = pstmt.getGeneratedKeys();


            if(rs.next()){
                user.setId(rs.getLong(1));          //1번째 컬럼값 반환
                user.setNic_name(rs.getString(4));  //4번째 컬럼값 반환


            }else {
                throw new SQLException("id 조회 실패");

            }
            return Optional.of(user);

        }catch (Exception e){
            throw new IllegalStateException(e);


        }finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<User> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();

            List<User> users = new ArrayList<>();

            while(rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setNic_name(rs.getString("nic_name"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setIntroduction(rs.getString("introduction"));
                user.setPassword(rs.getString("password"));
                user.setWebSite(rs.getString("webSite"));
                user.setProfile_img(rs.getString("profile_img"));
                users.add(user);
            }
            return users;

        }catch (SQLException e){
            throw new IllegalStateException(e);
        }finally {
            close(conn, pstmt, rs);

        }
    }


    private Connection getConnection()  {
        return DataSourceUtils.getConnection(dataSource);

    };
    private void close (Connection conn, PreparedStatement pstmt, ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
               pstmt.close();

            }
        }catch(SQLException e){
            e.printStackTrace();
        }
        try{
            if(conn != null){
                close(conn);
            }
        }catch (SQLException e ){
            e.printStackTrace();
        }
    }

    private void close(Connection conn) throws SQLException{
        DataSourceUtils.releaseConnection(conn, dataSource);
    }
}
