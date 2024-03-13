package hello.itemservice.web.basic;

import hello.itemservice.domain.item.Item;
import hello.itemservice.domain.item.ItemRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/basic/items")
@RequiredArgsConstructor
public class BasicItemController {

    private final ItemRepository itemRepository;

    @GetMapping
    public String items(Model model){

        // /basic/items 조회시 상품 목록 화면 출력하기

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "basic/items";
    }

    //
    @GetMapping("/{itemId}")
    public String item(@PathVariable Long itemId,Model model){
        // 상품 상세 조회
        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "basic/item";
    }

    @GetMapping("/add")
    public String add(){
        //상품 등록 페이지
        return "basic/addForm";
    }

    //등록한 상품 저장하기
//    @PostMapping("/add")
    public String addItemV1(@RequestParam String itemName,
                       @RequestParam int price,
                       @RequestParam int  quantity,
                       Model model){

        Item item = new Item();
        item.setItemName(itemName);
        item.setPrice(price);
        item.setQuantity(quantity);

        itemRepository.save(item);

        // 상품 저장후 상세화면으로 저장한 상품 내용 보여주기
        model.addAttribute("item", item);

        return "basic/item";
    }

    //등록한 상품 저장하기
//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute("item") Item item){

        itemRepository.save(item);

        // 상품 저장후 상세화면으로 저장한 상품 내용 보여주기
//        model.addAttribute("item", item); // 자동 추가, 생략가능

        return "basic/item";
    }

    //등록한 상품 저장하기
//    @PostMapping("/add")
    public String addItemV5(Item item){

        itemRepository.save(item);
        return "redirect:/basic/items/"+item.getId();
    }
    //등록한 상품 저장하기
    @PostMapping("/add")
    public String addItemV6(Item item, RedirectAttributes redirectAttribute){

        Item savedItem = itemRepository.save(item);
        redirectAttribute.addAttribute("itemId",savedItem.getId());
        redirectAttribute.addAttribute("status",true);
        return "redirect:/basic/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId,Model model){

        Item item = itemRepository.findById(itemId);
        // 찾은 상품에 대한 데이터 뷰로 전달하기
        model.addAttribute("item", item);

        return "/basic/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId,@ModelAttribute Item item){
    // 상품 수정후 저장하기
        itemRepository.update(itemId,item);
         return  "redirect:/basic/items/{itemId}";
    }


    /*
     * 테스트용 데이터 추가
     * */
    @PostConstruct
    public void init(){
        itemRepository.save(new Item("itemA", 10000, 2));
        itemRepository.save(new Item("itemB", 5000, 6));
    }

}
