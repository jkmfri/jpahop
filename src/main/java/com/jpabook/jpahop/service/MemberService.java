package com.jpabook.jpahop.service;

import com.jpabook.jpahop.domain.Member;
import com.jpabook.jpahop.repository.MemberRepository2;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
/**
 * - class 레벨에 Transactional을 사용 하면 포함된 method에 transaction이 걸린다.
 * - Transactional은 javax 와 spring 2가지가 있는데, spring에 더 많은 기능이 있음.
 * - class 레벨에 readOnly를 사용하면 기본적으로 전체가 readOnly 적용,
 * - 필요한 method에 별도로 @Transactional 설정
 * - method에 설정된 @Transactional이 우선권을 갖는다.
 */
@Transactional(readOnly = true)
/**
 * @AllArgsConstructor //Lombok - 생성자 자동생성, MemberRepository의 생성자 주입 부분을 작성할 필요 없음
 * @RequiredArgsConstructor //Lombok - final 있는 필드만 가지고 생성자를 자동 생성
 *
 */
@RequiredArgsConstructor
public class MemberService {
    /** Bean을 주입 하는 방법*/
    /* //1.
    @Autowired
    private MemberRepository memberRepository;*/

    /* //2. Setter Injection - Test코드 작성시 Mock을 직접 주입할 수 있다.
    *  //- Setter Inejction은 변경이 발생 할 수 있다. 따라서, 권장하지 않음
    private MemberRepository memberRepository;

    @Autowired
    public void setMemberRepository(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }*/

    /* //3. 생성자 주입
    *  // Spring이 뜰때 생성자에서 주입을 한다.
    *  // TestCase 작성시 MemberRepository에 Mock을 사용 할 수 있다.
    private final MemberRepository memberRepository; // final 추천

    @Autowired
    public MemberService(MemberRepository memberRepository){
        this.memberRepository = memberRepository;
    }
    */

    private final MemberRepository2 memberRepository2; // Lombok RequiredArgsConstructor에 의해 생성자 자동생성성

   /**
     * 회원가입
     */
    // Transactional을 여기에 작성하면 해당 메소드에만 적용
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복회원 검증
        memberRepository2.save(member);
        return member.getId();
    }

    private void validateDuplicateMember(Member member) {
        List<Member> findMembers = memberRepository2.findByName(member.getName());
        if( !findMembers.isEmpty() ){
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    /*
    * @Transactional(readOnly = true) // 읽기인 경우에는 readOnly를 넣어준다. - JPA가 조회하는 것에서는 최적화 해줌
    */
    // 회원 전체 조회
    public List<Member> findMembers(){
        return memberRepository2.findAll();
    }

    public Member findOne(Long memberId){
        return memberRepository2.findOne(memberId);
    }
}
