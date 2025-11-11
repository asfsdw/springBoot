package com.example.demo4.dto;

public class ExamDTO {
  private int idx;
  private String name;
  private String pwd;
  private int age;
  private String gender;
  private String job;
  private String mountain;

  public ExamDTO() {}

  public ExamDTO(String name, int age) {
    this.name = name;
    this.age = age;
  }

  public int getIdx() {
    return idx;
  }

  public String getName() {
    return name;
  }

  public String getPwd() {
    return pwd;
  }

  public int getAge() {
    return age;
  }

  public String getGender() {
    return gender;
  }

  public String getJob() {
    return job;
  }

  public String getMountain() {
    return mountain;
  }

  public void setIdx(int idx) {
    this.idx = idx;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setPwd(String pwd) {
    this.pwd = pwd;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  public void setJob(String job) {
    this.job = job;
  }

  public void setMountain(String mountain) {
    this.mountain = mountain;
  }

  @Override
  public String toString() {
    return "examDTO{" +
        "idx=" + idx +
        ", name='" + name + '\'' +
        ", pwd='" + pwd + '\'' +
        ", age=" + age +
        ", gender='" + gender + '\'' +
        ", job='" + job + '\'' +
        ", mountain='" + mountain + '\'' +
        '}';
  }
}
