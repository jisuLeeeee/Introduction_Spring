package hello.hellospring.controller;

public class MemberForm {
    // createMemberForm.html의 <input>태그 name 속성 과 매칭됨
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
