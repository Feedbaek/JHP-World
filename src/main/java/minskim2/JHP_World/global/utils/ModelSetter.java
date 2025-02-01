package minskim2.JHP_World.global.utils;

import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;

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
     * @param  currentPage 현재 페이지 0부터 시작
     * */
    public static void setPaging(Model model, Integer currentPage, Integer totalPages) {
        if (currentPage == null || totalPages == null) {
            return;
        }
        List<Integer> pageNums = new ArrayList<>();
        int p = ++currentPage;

        while (pageNums.size() < 5) {
            int num = p++ - 2;
            if (num > totalPages) {
                break;
            }
            pageNums.add(num);
            if (pageNums.getLast() <= 0){
                pageNums.removeLast();
            }
        }

        model.addAttribute("pageNums", pageNums);
        model.addAttribute("currentPage", currentPage);
        model.addAttribute("totalPages", totalPages);
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
    public static void init(Model model, String title, Integer page, Integer totalPages, String url) {
        setTitle(model, title);
        setPaging(model, page, totalPages);
        setCurrentUri(model, url);
    }
}
