package zekisanmobile.petsitter.util;

import java.util.Date;

public class ContactStatusString {

    public static String getStatusName(int status, Date contactDateStart, Date contactDateFinal){
        switch (status){
            case 10:
                return "Próximo";
            case 20:
                return "Recusado";
            case 30:
                if(contactDateStart.getDate() >= new Date().getDate() &&
                        new Date().getDate() <= contactDateFinal.getDate()){
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

    public static String contatPeriod(Date dateStart, Date dateFinal){
        return Formatter.formatDateToString(dateStart)
                + " - "
                + Formatter.formatDateToString(dateFinal);
    }
}
