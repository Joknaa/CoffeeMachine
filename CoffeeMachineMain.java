import java.util.Scanner;

class CoffeeMachineMain {
    static MachineStates CurrentState;
    static CoffeeMachine CoffeeMachine1 = new CoffeeMachine();
    static Scanner sc = new Scanner(System.in);

    static public String GetInput(){
        String UserInput = "";
        UserInput = sc.next();
        return UserInput;
    }
    
    public static void main(String[] args) {
        String Option;

        while (true) {
            CurrentState = MachineStates.ChoosingMainOption;
            System.out.println();
            System.out.println("##########################[ MainMenu ]##########################");
            System.out.println("|| ----------------->  buy  : ORDER COFFEE  <---------------- ||");
            System.out.println("|| ----------------->  fill : FILL MACHINE  <---------------- ||");
            System.out.println("|| ----------------->  take : TAKE MONEY    <---------------- ||");
            System.out.println("|| -----------------> check : CHECK CONTENT <---------------- ||");
            System.out.println("|| ----------------->  exit : TURN OFF      <---------------- ||");
            System.out.println("################################################################");
            System.out.printf("#-> Option : ");
            Option = GetInput();

            if (Option.compareTo("buy") == 0){
                CurrentState = MachineStates.ChoosingCoffeeType;
                CoffeeMachine1.OrderCupOfCoffee();

            } else if (Option.compareTo("fill") == 0){
                CurrentState = MachineStates.FillingTheMachine;
                CoffeeMachine1.FillMachineWithIngredients();

            } else if (Option.compareTo("take") == 0){
                CurrentState = MachineStates.TakingTheMoney;
                CoffeeMachine1.TakeMoneyFromMachine();

            } else if (Option.compareTo("check") == 0){
                CurrentState = MachineStates.CheckingTheRemaining;
                CoffeeMachine1.GetMachineIngredients();

            } else if (Option.compareTo("exit") == 0){
                CurrentState = MachineStates.ShutingSown;
                return;
            }
        }
    }
}        