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
        if (currentPage < 0) {
            return;
        }
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
    /**
     * 타이틀 설정 메서드
     * */
    public static void setTitle(Model model,String Title, String title) {
        model.addAttribute("Title", Title);
        model.addAttribute("title", title);
    }

    /**
     * Model 초기화 메서드
     */
    public static void init(Model model, String Title, String title, int page, String url) {
        setTitle(model, Title, title);
        setPaging(model, page);
        setCurrentUri(model, url);
    }
}
