public class BlocoControle{

    //Sinais aluOp0, aluOp1 e lui vão para o ULA Control, para definir a operação a ser feita na ULA
    //Se lui = 1, ula instruction = sll
    //Senão, aluOp0/1 01 = Type-R (define instruction pela funct), 00 = Lw/Sw/Addiu (intruction = Add)
    //10 = Beq (instruction = Sub), 11 = ori (instruction = or)
    int regWrite = 0;
    int regDst = 0;
    int aluSrc = 0;
    int branch = 0;
    int memWrite = 0;
    int memRead = 0;
    int memToReg = 0;
    int aluOp0 = 0;
    int aluOp1 = 0;
    int jump = 0;
    int lui = 0;

    //Teria como já começar com isso inicializado? Em tese só vai ter uma instância dessa classe.

    // opcode[6]
    public void controlByOpCode (String opCode) {

        //R-type
        if (opCode == "000000") {

            regWrite = 1;
            regDst = 1;
            aluSrc = 0;
            branch = 0;
            memWrite = 0;
            memRead = 0;
            memToReg = 0;
            aluOp0 = 0;
            aluOp1 = 1;
            jump = 0;
            lui = 0;

        }

        //Lw
        if (opCode == "100011") {

            regWrite = 1;
            regDst = 0;
            aluSrc = 1;
            branch = 0;
            memWrite = 0;
            memRead = 1;
            memToReg = 1;
            aluOp0 = 0;
            aluOp1 = 0;
            jump = 0;
            lui = 0;

        }

        //Sw
        if (opCode == "101011") {

            regWrite = 0;
            regDst = 0;
            aluSrc = 1;
            branch = 0;
            memWrite = 1;
            memRead = 0;
            memToReg = 0;
            aluOp0 = 0;
            aluOp1 = 0;
            jump = 0;
            lui = 0;

        }

        //Beq
        if (opCode == "000100") {

            regWrite = 0;
            regDst = 0;
            aluSrc = 0;
            branch = 1;
            memWrite = 0;
            memRead = 0;
            memToReg = 0;
            aluOp0 = 1;
            aluOp1 = 0;
            jump = 0;
            lui = 0;

        }

        //Addiu
        if (opCode == "001001") {

            regWrite = 1;
            regDst = 0;
            aluSrc = 1;
            branch = 0;
            memWrite = 0;
            memRead = 0;
            memToReg = 0;
            aluOp0 = 0;
            aluOp1 = 0;
            jump = 0;
            lui = 0;

        }

        //Ori - Usa o aluOp0/1 com 11, vai ser usado para passar uma instrução or para a ULA
        if (opCode == "001101") {

            regWrite = 1;
            regDst = 0;
            aluSrc = 1;
            branch = 0;
            memWrite = 0;
            memRead = 0;
            memToReg = 0;
            aluOp0 = 1;
            aluOp1 = 1;
            jump = 0;
            lui = 0;

        }

        //J
        if (opCode == "101011") {

            regWrite = 0;
            regDst = 0;
            aluSrc = 0;
            branch = 0;
            memWrite = 0;
            memRead = 0;
            memToReg = 0;
            aluOp0 = 0;
            aluOp1 = 0;
            jump = 1;
            lui = 0;

        }

        //Lui - Sinal de controle lui criado para passar a instrução sll para a ULA.
        if (opCode == "001111") {

            regWrite = 1;
            regDst = 0;
            aluSrc = 1;
            branch = 0;
            memWrite = 0;
            memRead = 0;
            memToReg = 0;
            aluOp0 = 0;
            aluOp1 = 0;
            jump = 0;
            lui = 1;

        }

    }

    //Funções diferentes para MUXs diferentes. Terceiro parâmetro já indica o(s) sinal(is) de controle a ser(em) usado(s).
    public static int writeRegMux(int ent0, int ent1, int regDst){
        if(regDst == 0){return ent0;}
        if(regDst == 1){return ent1;}
        System.out.println("Erro na operação de controle do writeRegMux!!!");
        return -1;
    }

    public static int aluOp2Mux(int ent0, int ent1, int aluSrc){
        if(aluSrc == 0){return ent0;}
        if(aluSrc == 1){return ent1;}
        System.out.println("Erro na operação de controle do aluOP2Mux!!!");
        return -1;
    }

    public static int memToRegMux(int ent0, int ent1, int memToReg){
        if(memToReg == 0){return ent0;}
        if(memToReg == 1){return ent1;}
        System.out.println("Erro na operação de controle do writeRegMux!!!");
        return -1;
    }

    //Define a operação da ULA com base nos inputs de controle. Retorna operação conforme códigos do switch do ULA.java
    // funct[6]
    public static int ulaControl(int ulaOp0, int ulaOp1, int lui, String funct){
        if(lui == 1)
            return 5;

        else if (ulaOp0 == 0 && ulaOp1 == 0)
            return 2;

        else if (ulaOp0 == 1 && ulaOp1 == 1)
            return 1;

        else if (ulaOp0 == 1 && ulaOp1 == 0)
            return 6;

        else if (ulaOp0 == 0 && ulaOp1 == 1)
            if (funct == "000000")
                return 5;
            if (funct == "000010")
                return 4;
            if (funct == "100000")
                return 2;
            if (funct == "100010")
                return 6;
            if (funct == "100100")
                return 0;

        System.out.println("Erro na escooa de operação da ulaControl!!!");
        return -1;
    }

}