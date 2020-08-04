

const axios = require("axios");
const cheerio = require("cheerio");
const log = console.log;

const getHtml = async() => {
    try{
        return await axios.get("https://search.naver.com/search.naver?sm=tab_hty.top&where=nexearch&query=%ED%99%8D%EB%8C%80+%EB%A7%9B%EC%A7%91&oquery=%EB%84%A4%EC%9D%B4%EB%B2%84+%EB%A7%9B%EC%A7%91&tqi=UyQjRwprvTVssCPgGa4ssssstzs-144912"); // axios.get 함수 이용하여 비동기로 네이버의 html 파일 가져온다
    } catch(error){
        console.error(error);
    }
};

getHtml()
    .then(html => {
        let ulList = [];
        const $ = cheerio.load(html); 
        const $bodyList = $("div.list_area ul.list_place_col1").children("li.list_item.type_restaurant");

        $bodyList.each(function(i, elem){
            ulList[i] = {
                title: $(this).find('a.name').text(),
                url: $(this).find('div.thumb').attr('src')

            };
        });

        const $foodList = $('div.list_area ul.list_place_col1')

        console.log($foodList.html())


        console.log($bodyList.html());
        const data = ulList.filter(n => n.title);
        return data;
    })
    .then(res => log(res));

