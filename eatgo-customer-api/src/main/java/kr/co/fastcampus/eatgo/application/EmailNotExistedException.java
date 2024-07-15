package kr.co.fastcampus.eatgo.application;

public class EmailNotExistedException extends RuntimeException {

    EmailNotExistedException() {
        super("Email is not existed");
    }

}
