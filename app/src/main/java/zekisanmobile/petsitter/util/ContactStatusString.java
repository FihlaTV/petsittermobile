package zekisanmobile.petsitter.util;

import java.util.Date;

public class ContactStatusString {

    public static String getStatusName(int status, String contactDateStart, String contactDateFinal){
        switch (status){
            case 10:
                return "Próximo";
            case 20:
                return "Recusado";
            case 30:
                if(Formatter.formattedDate(contactDateStart).getDate() >= new Date().getDate() &&
                        new Date().getDate() <= Formatter.formattedDate(contactDateFinal).getDate()){
                    return "Atual";
                }else{
                    return "Próximo";
                }
            case 40:
                return "Finalizado";
            default: // 50
                return "Atrasado";
        }
    }

    public static String contatPeriod(String dateStart, String dateFinal){
        return Formatter.formattedDateFromString(dateStart)
                + " - "
                + Formatter.formattedDateFromString(dateFinal);
    }
}
