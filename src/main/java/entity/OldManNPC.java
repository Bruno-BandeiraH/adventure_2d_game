package entity;

import main.GamePanel;

import java.util.Random;

public class OldManNPC extends Entity{

    public OldManNPC(GamePanel gp) {
        super(gp);
        direction = "down";
        speed = 1;
        getOldManImage();
        setDialogue();
    }

    public void getOldManImage(){
        up1 = setup("/npc/oldman_up_1");
        up2 = setup("/npc/oldman_up_2");
        down1 = setup("/npc/oldman_down_1");
        down2 = setup("/npc/oldman_down_2");
        left1 = setup("/npc/oldman_left_1");
        left2 = setup("/npc/oldman_left_2");
        right1 = setup("/npc/oldman_right_1");
        right2 = setup("/npc/oldman_right_2");
    }

    public void setDialogue() {
        dialogues[0] = "Oopa, meu querido. Tudo bem?";
        dialogues[1] = "Então tu veio pra essa ilha pra lançar a \nbraba?";
        dialogues[2] = "Eu era muito popular com as garotas,\nmas agora estou muito velho para\nnamorar.";
        dialogues[3] = "Bom, espero que dê tudo certo para\nvocê, meu rapaz.";
    }

    public void setAction() {

        animationIntervalCounter++;

        if(animationIntervalCounter == 120){
            Random random = new Random();
            int i = random.nextInt(100)+1; // 1 to 100
            if(i <= 25) {
                direction = "up";
            }
            if (i > 25 && i <= 50) {
                direction = "down";
            }
            if(i > 50 && i <= 75) {
                direction = "left";
            }
            if(i > 75) {
                direction = "right";
            }
            animationIntervalCounter = 0;
        }
    }

    public void speak() {
        super.speak();
    }

}
