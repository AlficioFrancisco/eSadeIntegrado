/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlpg;

import conexao.Jpa;
import control.UsersJpaController;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import modelo.Users;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Listen;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Label;
import org.zkoss.zul.Textbox;
import servicos.Autenticacao;
import servicos.AutenticacaoImpl;

/**
 *
 * @author ali_sualei
 */
public class login extends SelectorComposer<Component> {

    private static final long serialVersionUID = 1L;

    //wire components
    @Wire
    Textbox account;
    @Wire
    Textbox password;
    @Wire
    Label message;

    //services
    Autenticacao authService = new AutenticacaoImpl();

    @Listen("onClick=#login; onOK=#loginWin")
    public void doLogin() throws NoSuchAlgorithmException {
        String nm = account.getValue();
        String pd = password.getValue();
        List<Users> users = new UsersJpaController(new Jpa().getEmf()).findUsersEntities();
        MessageDigest algoritmo = MessageDigest.getInstance("MD5");
        for (Users user : users) {
            if (user.getUtilizador().equals(nm) && user.getPasword().equals(pd) && user.getIdGrupo().getIdGrupo().equals("recursos humanus")){ 
                Executions.sendRedirect("/listaParametroAuto.zul");
                return;
            }
            else
            if (user.getUtilizador().equals(nm) && user.getPasword().equals(pd) && user.getIdGrupo().getIdGrupo().equals("superior hierarquico")){ 
                Executions.sendRedirect("/superiorh1.zul");
                return;
            }
                else
            if (user.getUtilizador().equals(nm) && user.getPasword().equals(pd) && user.getIdGrupo().getIdGrupo().equals("docente")){ 
                Executions.sendRedirect("/autoavaliacao2.zul");
                return;
            }
                else
            if (user.getUtilizador().equals(nm) && user.getPasword().equals(pd) && user.getIdGrupo().getIdGrupo().equals("Estudante")) {
                Executions.sendRedirect("/avaliacao2.zul");
                return;
            }
//            else
//              message.setValue("Nome do utilizador ou senha incorrectos!");  
             
            

        }
//        if (Strings.isBlank(nm) || Strings.isBlank(pd)) {
//            message.setValue("Preencha todos os campos");
//            message.setVisible(true);
//        } else {
//            if (authService.login(nm, pd) == 1) {
//                message.setValue("Nome do utilizador ou senha incorrectos!");
//                message.setVisible(true);
//                return;
//            }
//
//            if (authService.login(nm, pd) == 2) {
//                message.setValue("Sua conta esta bloqueada!");
//                message.setVisible(true);
//                return;
//            }
//            if (authService.login(nm, pd) == 3) {
//                UserCredential cre = authService.getUserCredential();
//                Executions.sendRedirect("/superioh1.zul");
//            }
//            if(authService.login(nm, pd) == 4){
//                UserCredential cre = authService.getUserCredential();
//                Executions.sendRedirect("/autoavaliacao2.zul");
//            }
//            if (authService.login(nm, pd) == 5) {
//                UserCredential cre = authService.getUserCredential();
//                Executions.sendRedirect("/avaliacao2.zul");
//            }
//            if (authService.login(nm, pd) == 6) {
//                message.setValue("Nao tem permissao para aceder ao sistema!");
//                message.setVisible(true);
//            }
//        }
//    }
//
//    @Listen("onChanging = #account; onChanging =#password")
//    public void doHideMessage() {
//        message.setVisible(false);
//    }
//    
//}
    }
}
