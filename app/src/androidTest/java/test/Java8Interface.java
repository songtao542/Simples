package test;

public interface Java8Interface{
    default void dmethod(){
        System.out.print("This is default method!");
    }
}