package board.apiproject.Repository;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;
import board.apiproject.dto.Member;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Component //하나뿐인 생성자를 자동 의존 주입하므로 Test 시 확인해보겠음
@Repository
public class MemberRepositoryImpl implements MemberRepository{

    private final JdbcTemplate jdbcTemplate;
    private final DataSource dataSource;

//    @Autowired
    public MemberRepositoryImpl(DataSource dataSource) {

        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.dataSource = dataSource;
    }

    private final RowMapper<Member> memberRowMapper = (resultSet, rowNum) -> {
        Member find_member = new Member();
        find_member.setNum( resultSet.getInt("num"));
        find_member.setId(resultSet.getString("id"));
        find_member.setPass(resultSet.getString("pass"));
        find_member.setName(resultSet.getString("name"));
        find_member.setAge(resultSet.getInt("age"));
        find_member.setEmail(resultSet.getString("email"));
        find_member.setPhone(resultSet.getString("phone"));
        find_member.setGrant(resultSet.getString("usergrant"));
        return find_member;
    };

    @Override
    public Member save(Member member) {


        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);

        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("num");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("id", member.getId());
        parameters.put("pass", member.getPass());
        parameters.put("name", member.getName());
        parameters.put("age", member.getAge());
        parameters.put("email", member.getEmail());
        parameters.put("phone", member.getPhone());
        parameters.put("grant", member.getGrant());
        Number num = jdbcInsert.executeAndReturnKey(new MapSqlParameterSource(parameters));
        System.out.println("num = " + num);
        member.setNum(num.intValue());

        return member;

    }

    @Override
    public Member findById(String id) {
        String SQL ="select * from member where id=?";
        // query()
        //Querying and returning multiple objects(여러 개 객체)
        // 밑에건느 하나만 조회~~
        try {
            Member member = jdbcTemplate.queryForObject(SQL, memberRowMapper, id);
            System.out.println("member = " + member);
            return member;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<Member> findAll() {
        String SQL ="select * from member";
        try{
            return this.jdbcTemplate.query(SQL,memberRowMapper);
        }catch (Exception e){
            return null;
        }
    }
}
