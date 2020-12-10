import java.util.Scanner;
enum MachineStates {
    ChoosingMainOption,
    ChoosingCoffeeType,
    FillingTheMachine,
    CheckingTheRemaining,
    TakingTheMoney,
    ShutingSown
}
enum Menu {
    Espresso   (250,   0,  16,   4),
    Latte      (350,  75,  20,   7),
    Cappuccino (200, 100,  12,   6);

    private Menu(int Water, int Milk, int Coffee, int Price) {
        this.Water  = Water;
        this.Milk   = Milk;
        this.Coffee = Coffee;
        this.Price  = Price;
    }

    int Water;
    int Milk;
    int Coffee;
    int Price;
}

class CoffeeMachine{
    MachineStates CurrentState;
    Scanner sc = new Scanner(System.in);

    int totalWater           =  400;
    int totalMilk            =  540;
    int totalCoffee          =  120;
    int totalDisposableCups  =    9;
    int totalCash            =  550;

    int neededTypeOfCoffee;
    int neededCupsOfCoffee;

    int affordableWaterUnits;
    int affordableMilkUnits;
    int affordableCoffeeUnits;
    int affordableDisposableCups;
    int affordableCupsOfCoffee;
    int tmp;

    public String GetInput(){
        String UserInput = "";
        
        UserInput = sc.next();
        return UserInput;
    }

    public void OrderCupOfCoffee() {
        System.out.println("########################[ OrderCoffee ]#########################");
        System.out.println("|| --------------------> 1 : ESPRESSO   <-------------------- ||");
        System.out.println("|| --------------------> 2 : LATTE      <-------------------- ||");
        System.out.println("|| --------------------> 3 : CAPPUCCINO <-------------------- ||");
        System.out.println("|| --------------------> 0 : MAIN MENU  <-------------------- ||");
        System.out.println("################################################################");
        System.out.printf("#-> Option : ");
        String Choice = GetInput();

        if (Choice.compareTo("0") == 0){
            return;
        } else if (Choice.compareTo("1") == 0){
            neededTypeOfCoffee = 1;
        } else if (Choice.compareTo("2") == 0){
            neededTypeOfCoffee = 2;
        } else if (Choice.compareTo("3") == 0){
            neededTypeOfCoffee = 3;
        }
        System.out.printf("#-> Enter number of cups : ");
        neededCupsOfCoffee = Integer.parseInt(GetInput());
        affordableCupsOfCoffee = GetMaxCupsAffordable(neededTypeOfCoffee);

        if (affordableCupsOfCoffee == neededCupsOfCoffee) {   
            System.out.println("# >> Yes, I can make that amount of coffee << ");
            MakeCoffee(neededTypeOfCoffee, neededCupsOfCoffee);
            GetPaid(neededTypeOfCoffee, neededCupsOfCoffee);
        } else if (affordableCupsOfCoffee < neededCupsOfCoffee) {
            System.out.println("# >> No, I can make only " + affordableCupsOfCoffee + " cup(s) of coffee << ");
        } else if (affordableCupsOfCoffee > neededCupsOfCoffee) {
            tmp = affordableCupsOfCoffee - neededCupsOfCoffee;
            System.out.println("# >> Yes, I can make that amount of coffee (and even " + tmp + " more than that) << ");
            MakeCoffee(neededTypeOfCoffee, neededCupsOfCoffee);
            GetPaid(neededTypeOfCoffee, neededCupsOfCoffee);
        }
    }

    public int GetMaxCupsAffordable(int neededTypeOfCoffee) {
        int affordableCupsOfCoffee = 0;

        if(neededTypeOfCoffee == 1) {
            affordableWaterUnits  = (totalWater  - (totalWater  % Menu.Espresso.Water))  / Menu.Espresso.Water;
            affordableCoffeeUnits = (totalCoffee - (totalCoffee % Menu.Espresso.Coffee)) / Menu.Espresso.Coffee;
            affordableCupsOfCoffee = affordableCoffeeUnits >= affordableWaterUnits ? affordableWaterUnits : affordableCoffeeUnits;
        } else if(neededTypeOfCoffee == 2) {
            affordableWaterUnits    = (totalWater  - (totalWater  % Menu.Latte.Water))  / Menu.Latte.Water;
            affordableMilkUnits     = (totalMilk   - (totalMilk   % Menu.Latte.Milk))   / Menu.Latte.Milk;
            affordableCoffeeUnits   = (totalCoffee - (totalCoffee % Menu.Latte.Coffee)) / Menu.Latte.Coffee;
        } else if(neededTypeOfCoffee == 3) {
            affordableWaterUnits    = (totalWater  - (totalWater  % Menu.Cappuccino.Water))  / Menu.Cappuccino.Water;
            affordableMilkUnits     = (totalMilk   - (totalMilk   % Menu.Cappuccino.Milk))   / Menu.Cappuccino.Milk;
            affordableCoffeeUnits   = (totalCoffee - (totalCoffee % Menu.Cappuccino.Coffee)) / Menu.Cappuccino.Coffee;
        }

        if(neededTypeOfCoffee != 1) {
            if (affordableWaterUnits >= affordableMilkUnits) {
                affordableCupsOfCoffee = affordableMilkUnits >= affordableCoffeeUnits ? affordableCoffeeUnits : affordableMilkUnits;
            } else {
                affordableCupsOfCoffee = affordableWaterUnits >= affordableCoffeeUnits ? affordableCoffeeUnits : affordableWaterUnits;
            }
        }
        return affordableCupsOfCoffee;
    }

    public void MakeCoffee(int neededTypeOfCoffee, int neededCupsOfCoffee) {
        if(neededTypeOfCoffee == 1) {
            totalWater           -= Menu.Espresso.Water  * neededCupsOfCoffee;
            totalCoffee          -= Menu.Espresso.Coffee * neededCupsOfCoffee;
        } else if(neededTypeOfCoffee == 2) {
            totalWater           -= Menu.Latte.Water  * neededCupsOfCoffee;
            totalMilk            -= Menu.Latte.Milk   * neededCupsOfCoffee;
            totalCoffee          -= Menu.Latte.Coffee * neededCupsOfCoffee;
        } else if(neededTypeOfCoffee == 3) {
            totalWater           -= Menu.Cappuccino.Water  * neededCupsOfCoffee;
            totalMilk            -= Menu.Cappuccino.Milk   * neededCupsOfCoffee;
            totalCoffee          -= Menu.Cappuccino.Coffee * neededCupsOfCoffee;
        }
        totalDisposableCups  -= neededCupsOfCoffee;
    }

    public void GetPaid(int neededTypeOfCoffee, int neededCupsOfCoffee) {
        if(neededTypeOfCoffee == 1) {
            totalCash += Menu.Espresso.Price * neededCupsOfCoffee;
        } else if(neededTypeOfCoffee == 2) {
            totalCash += Menu.Latte.Price * neededCupsOfCoffee;
        } else if(neededTypeOfCoffee == 3) {
            totalCash += Menu.Cappuccino.Price * neededCupsOfCoffee;
        }
    }

    public void FillMachineWithIngredients() {
        System.out.println("########################[ FillMachine ]#########################");
        System.out.printf("#-> Add Water in mL      : ");
        totalWater          += Integer.parseInt(GetInput());
        System.out.printf("#-> Add Milk in mL       : ");
        totalMilk           += Integer.parseInt(GetInput());
        System.out.printf("#-> Add Coffee beans in g : ");
        totalCoffee         += Integer.parseInt(GetInput());
        System.out.printf("#-> Add Disposable cups  : ");
        totalDisposableCups += Integer.parseInt(GetInput());
    }

    public void TakeMoneyFromMachine() {
        System.out.println("#########################[ TakeMoney ]##########################");
        System.out.println("#-> Total Money in the machine :  " + totalCash + " $.");
        System.out.println("# >> Money withdrawn << ");


        totalCash = 0;
    }

    public void GetMachineIngredients() {
        System.out.println("########################[ CheckContent ]########################");
        System.out.println("#---> The coffee machine has: ");
        System.out.println("#-> Water           : " + totalWater + " mL.");
        System.out.println("#-> Milk            : " + totalMilk + " mL.");
        System.out.println("#-> Coffee beans    : " + totalCoffee + " g.");
        System.out.println("#-> Disposable cups : " + totalDisposableCups);
        System.out.println("#-> Money           : " + totalCash + " $.");
    }
}        