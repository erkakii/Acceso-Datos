import java.util.ArrayList;

public class Columna {
    private String cabecera;
    private ArrayList<String> fila;

    public Columna() {
        cabecera = "";
        fila = new ArrayList<>();
    }

    public String getCabecera() {
        return cabecera;
    }

    public void setCabecera(String cabecera) {
        this.cabecera = cabecera;
    }

    public ArrayList<String> getFila() {
        return fila;
    }

    public void setFila(ArrayList<String> fila) {
        this.fila = fila;
    }

    @Override
    public String toString() {
        return "Columna{" +
                "cabecera='" + cabecera + ' ' +
        ", fila=" + fila +
                '}';
    }
}

