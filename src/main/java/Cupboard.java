public class Cupboard<T> {
    
    public Cupboard(T item) {
        System.out.println("Cupboard for " + item.getClass());
    }

    public static void main(String [] args) {
	Cupboard<String> c1=new Cupboard<String>("dishes");
	Cupboard<Integer> c2=new Cupboard<Integer>(123);
	Cupboard<Double> c3=new Cupboard<Double>(3.14159);
    }
}
