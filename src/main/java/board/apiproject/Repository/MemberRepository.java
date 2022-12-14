package board.apiproject.Repository;

import board.apiproject.dto.Member;

import java.util.List;

public interface MemberRepository {
    public Member save(Member member);

    public Member findById(String id);

    public List<Member> findAll(); // 모든 회원 찾기
}
