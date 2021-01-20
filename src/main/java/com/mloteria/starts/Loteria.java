package com.mloteria.starts;

import com.mloteria.Lottery;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Iterator;

public class Loteria {

    public static boolean iniciou = false;

    public static boolean jaIniciou () {
        return iniciou;

    }

    public static void setIniciou (boolean b) {
        iniciou = b;
    }

    public static boolean numeroCorreto(int numero) {
        return Lottery.getInstance().getConfig().getInt("Loteria.Numero") == numero;
    }

    public static void iniciar (final int avisos, final int numerocorreto) {
        if (avisos > 0) {
            setIniciou(true);
            for (String lore : Lottery.getInstance().getConfig().getStringList("Loteria.Anuncio")) {
                String lorereplaced = lore.replace('&', '§');
                Bukkit.broadcastMessage(lorereplaced);
            }
            Lottery.getInstance().getConfig().set("Loteria.Numero", numerocorreto);
            Lottery.getInstance().saveConfig();
            Bukkit.getScheduler().runTaskLater(Lottery.getInstance(), new Runnable() {
                public void run() {
                    Loteria.iniciar(avisos - 1, numerocorreto);
                }
            }, (long) (Lottery.getInstance().getConfig().getInt("Loteria.Segundos") * 20 ));
        }else {
            finalizar();
        }
    }

    public static boolean isNumber(String args) {
        try {
            Integer.parseInt(args);
            return true;
        } catch (NumberFormatException var2) {
            return false;
        }
    }

    public static void parar() {
        setIniciou(false);
        Bukkit.getScheduler().cancelTasks(Lottery.getInstance());
        Lottery.getInstance().getConfig().set("Loteria.Numero", 0);
        Lottery.getInstance().saveConfig();
        Bukkit.broadcastMessage(Lottery.getMensagens().getConfig().getString("Mensagens.Evento-Cancelado").replace('&', '§'));
    }
    public static void finalizar() {
        setIniciou(false);
        Bukkit.getScheduler().cancelTasks(Lottery.getInstance());
        Lottery.getInstance().getConfig().set("Loteria.Numero", 0);
        Lottery.getInstance().saveConfig();
    }

    public static void setVencedor(Player p, int numero) {
        Iterator var3 = Lottery.getMensagens().getConfig().getStringList("Mensagens.Vencedor-Loteria").iterator();

        while(var3.hasNext()) {
            String venceu = (String)var3.next();
            Bukkit.broadcastMessage(venceu.replace('&', '§').replace("@ganhador", p.getName()).replace("@numero", String.valueOf(Lottery.getInstance().getConfig().getInt("Loteria.Numero"))));
        }

        for (String comandos: Lottery.getInstance().getConfig().getStringList("Loteria.Premios")) {
            String comandosreplaced = comandos.replace('&', '§').replace("@player", p.getName());
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), comandosreplaced);
        }
        finalizar();
    }
}
