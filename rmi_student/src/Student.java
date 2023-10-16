import java.io.Serializable;

public class Student implements Serializable {
    private String code;
    private String name;
    private double score;

    public Student(String code, String name, double score) {
        this.code = code;
        this.name = name;
        this.score = score;
    }

    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }




    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}
