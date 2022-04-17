package kr.item;
 
public class OLikeVO {   //좋아요 테이블

   private int like_num;   //구매번호
   private int item_num;   //상품번호
   private int mem_num;   //구매자 회원번호   
   
   private OItemVO item;
   
   public int getLike_num() {
      return like_num;
   }
   public void setLike_num(int like_num) {
      this.like_num = like_num;
   }
   public int getItem_num() {
      return item_num;
   }
   public void setItem_num(int item_num) {
      this.item_num = item_num;
   }
   public int getMem_num() {
      return mem_num;
   }
   public void setMem_num(int mem_num) {
      this.mem_num = mem_num;
   }
   
   public OItemVO getItem() {
      return item;
   }
   public void setItem(OItemVO item) {
      this.item = item;
   }
   
}