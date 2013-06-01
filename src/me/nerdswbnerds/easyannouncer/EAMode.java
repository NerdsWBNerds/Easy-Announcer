package me.nerdswbnerds.easyannouncer;

public enum EAMode {
    ORDERED(1), RANDOM(2), ORGANIZED(3);

    public int id = -1;

    EAMode(int i){
        id = i;
    }

    public static EAMode getMode(int id){
        for(EAMode e: values()){
            if(e.id == id)
                return e;
        }

        return null;
    }
}
