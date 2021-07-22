package bht.expense.user;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Administrator
 * @version 1.0
 **/
@RunWith(SpringRunner.class)
public class TestBCrypt {


    @Test
    public void testBCrypt(){

        //对密码进行加密
        String password = BCrypt.hashpw("123456", BCrypt.gensalt());
        System.out.println("password = "+password);

        //校验密码
        boolean checkpw = BCrypt.checkpw("123456", "$2a$10$5OjJ5oBAMmdBEo2X365eZupDVqG1255BmMd.g2VYDN2fFwMwa2kQi");
       // boolean checkpw2 = BCrypt.checkpw("123", "$2a$10$HuClcUqr/FSLmzSsp9SHqe7D51Keu1sAL7tUAAcb..FyILiLdFKYy");
        System.out.println(checkpw);
        //System.out.println(checkpw2);
    }


}
