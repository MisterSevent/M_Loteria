package com.mloteria.commands;

import com.mloteria.Lottery;
import com.mloteria.starts.Loteria;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import java.util.Random;

import static com.mloteria.Lottery.*;

public class LoteriaCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(getMensagens().getConfig().getString("Mensagens.console-execute").replace('&', '§'));
            return true;
        }

        Player p = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("loteria")) {
            if (args.length == 0) {
                loreHelps(p);
                return true;
                }
            }

        //Se o args 1 for = iniciar e se o jogador não tiver a permissão para o codigo no return true;
        if (args[0].equalsIgnoreCase("iniciar")) {
            if (!p.hasPermission(getInstance().getConfig().getString("Permissions.admin"))) {
                p.sendMessage(getMensagens().getConfig().getString("Mensagens.not-permission").replace('&', '§'));
                return true;
            }
            //Boolean sempre vai true, então SE o boolean ON for = true, vai manda mensagem que a loteria já esta aberta;
            if (Loteria.jaIniciou()) {
                p.sendMessage(getMensagens().getConfig().getString("Mensagens.already-started").replace('&', '§'));
                return true;
            }else {
                Random rand = new Random();
                int numero = rand.nextInt(getInstance().getConfig().getInt("Loteria.Maximo"));
                Loteria.iniciar(getInstance().getConfig().getInt("Loteria.Anuncios"), numero);
                p.sendMessage(getMensagens().getConfig().getString("Mensagens.Loteria-Iniciada").replace('&', '§').replace("@numero", String.valueOf(numero)));
            }

        } else if (Loteria.isNumber(args[0])) {
            if (!Loteria.jaIniciou()) {
                p.sendMessage(getMensagens().getConfig().getString("Mensagens.Nao-Ocorrendo").replace('&', '§'));
            }else {
                int numero = Integer.parseInt(args[0]);
                if (Loteria.numeroCorreto(numero)) {
                    Loteria.setVencedor(p, numero);
                }else {
                    p.sendMessage(getMensagens().getConfig().getString("Mensagens.Numero-Errado").replace('&', '§'));
                }
            }
        }

        if (args[0].equalsIgnoreCase("parar")) {
            if (!p.hasPermission(getInstance().getConfig().getString("Permissions.admin"))) {
                p.sendMessage(getMensagens().getConfig().getString("Mensagens.not-permission").replace('&', '§'));
                return true;
            }
            if (!Loteria.jaIniciou()) {
                p.sendMessage(getMensagens().getConfig().getString("Mensagens.Nao-Ocorrendo").replace('&', '§'));
            }
            Loteria.parar();
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if (!p.hasPermission(getInstance().getConfig().getString("Permissions.admin"))) {
                p.sendMessage(getMensagens().getConfig().getString("Mensagens.not-permission").replace('&', '§'));
                return true;
            }
            if (Loteria.jaIniciou()) {
                p.sendMessage(getMensagens().getConfig().getString("Mensagens.reload-ocorrendo").replace('&', '§'));
                return true;
            }
            Lottery.getMensagens().reloadConfig();
            p.sendMessage(getMensagens().getConfig().getString("Mensagens.reload-sucess").replace('&', '§'));
            return true;
        }

        return false;
    }

    public void loreHelps(Player p) {
        if (p.hasPermission(getInstance().getConfig().getString("Permissions.admin"))) {
            for (String lore : getMensagens().getConfig().getStringList("Mensagens.Help.admin")) {
                String msg = lore.replace('&', '§');
                p.sendMessage(msg);
            }
        }else {
            for (String lore : getMensagens().getConfig().getStringList("Mensagens.Help.player")) {
                String msg = lore.replace('&', '§');
                p.sendMessage(msg);
            }

        }
    }
}

