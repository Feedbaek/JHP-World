package minskim2.JHP_World.domain.common;

import org.springframework.ui.Model;

public class ModelSetter {
    /**
     * 현재 URI를 설정하는 메서드
     * currentUri는 항상 끝에 ?가 붙어있어야 함
     * */
    public static void setCurrentUri(Model model, String currentUri) {
        // 쿼리 스트링이 없을 경우 ?를 붙여줌
        if (!currentUri.contains("?")) {
            currentUri += "?";
        }
        model.addAttribute("currentUri", currentUri);
    }
    /**
     * 페이징 처리를 위한 메서드
     * */
    public static void setPaging(Model model, Integer currentPage) {

        if (currentPage == null) {
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
    public static void setTitle(Model model, String title) {

        model.addAttribute("title", title);
    }

    /**
     * Model 초기화 메서드
     */
    public static void init(Model model, String title, Integer page, String url) {
        setTitle(model, title);
        setPaging(model, page);
        setCurrentUri(model, url);
    }
}
