package minskim2.JHP_World.domain.common;

import org.springframework.ui.Model;

public class ModelSetter {
    /**
     * 현재 URI를 설정하는 메서드
     * */
    public static void setCurrentUri(Model model, String currentUri) {
        model.addAttribute("currentUri", currentUri);
    }
    /**
     * 페이징 처리를 위한 메서드
     * */
    public static void setPaging(Model model, int currentPage) {
        int[] pageNums = new int[5];
        int p = currentPage;
        int i = 0;
        while (i < 5) {
            pageNums[i] = p++ - 2;
            if (pageNums[i] > 0){
                i++;
            }
        }
        model.addAttribute("pageNums", pageNums);
        model.addAttribute("currentPage", currentPage);
    }
}
