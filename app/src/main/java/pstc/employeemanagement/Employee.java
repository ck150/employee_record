package pstc.employeemanagement;

/**
 * Created by Chandrakant on 28-07-2016.
 */
public class Employee {
    public int id;
    public String name;
    public String doj; //date of joining
    public Performance performance;

    Employee(){
        this.name = "Employee";
        this.doj = "sample_doj";
        this.performance = new Performance(0);
    }


    Employee(String _name,String _doj,int pay){
        this.name = _name;
        this.doj = _doj;
        this.performance = new Performance(pay);
    }

    Performance getPerformance(){
        return this.performance;
    }

    void setPerformance(int rating,int pay){
        this.getPerformance().performance_rating = rating;
        this.getPerformance().pay_package = pay;
    }



    class Performance{
        int  performance_rating;
        int pay_package;
        Performance(int pay){
            this.performance_rating = 5;
            this.pay_package = pay;
        }

        void setPerformance_rating(int r){
            this.performance_rating = r;
        }
        void setPay_package(int p){
            this.pay_package = p;
        }
    }
}
