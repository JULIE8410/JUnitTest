package com.company;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

class ComputeWageTest {
    ComputeWage obj = new ComputeWage();

    @BeforeEach
    public void initEach(){

        //input 가로채기 - 아래의 유닛테스트들 실행하기 전에 input stream에 넣어둠
        ByteArrayInputStream in = new ByteArrayInputStream("John\n35".getBytes());

        // 버퍼에 넣어서 이미 input으로 만들어둠
        System.setIn(in);
    }


    @Test
    void acceptData() {
        obj.acceptData();
        assertEquals("John", obj.getName());
        assertEquals(35, obj.getHours());
    }

    @Test
    void computeWage() {
        obj.acceptData();
        obj.computeWage();
        assertEquals(525, obj.getGtotal());
    }

    @Test
    void computeWageWithOT() {
        //input 가로채기 - 아래의 유닛테스트들 실행하기 전에 input stream에 넣어둠
        ByteArrayInputStream in = new ByteArrayInputStream("John\n45".getBytes()); //682.5

        // 버퍼에 넣어서 이미 input으로 만들어둠
        System.setIn(in);

        obj.acceptData();
        obj.computeWage();
        assertEquals(682.5, obj.getGtotal());

    }

    @Test
    void TestException(){
        //input 가로채기 - 아래의 유닛테스트들 실행하기 전에 input stream에 넣어둠
        ByteArrayInputStream in = new ByteArrayInputStream("John\nxx\n35".getBytes());

        // 버퍼에 넣어서 이미 input으로 만들어둠
        System.setIn(in);

        PrintStream standardOut = System.out;
        ByteArrayOutputStream captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));


        obj.acceptData();


        //try,catch에 작성해둔 에러문장임 - 이 문장을 printout 시에 포함하고 있으면 예외가 발생한 것이므로 true 값
        assertTrue(new String(captor.toByteArray()).contains("You didn't enter a valid number"));

        System.setOut(standardOut);
    }



    @Test
    void display() {

        // will take over the printstream
        obj.acceptData();
        obj.computeWage();

        PrintStream standardOut = System.out;
        ByteArrayOutputStream captor = new ByteArrayOutputStream();
        System.setOut(new PrintStream(captor));

        obj.display();
           // System.out.println("The total wage of " + this.name + " is " + this.gtotal);
        assertEquals("The total wage of John is 525.0", captor.toString().trim());

        System.setOut(standardOut);

    }
}