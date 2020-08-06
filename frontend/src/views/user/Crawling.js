

const request = require('request-promise')
const cheerio = require('cheerio')
const v = require('voca')
const log = console.log;

request('https://map.naver.com/v5/search/%EC%9A%B0%EB%A7%88%EC%9D%B4%EB%8F%84%20%EA%B1%B4%EB%8C%80%EC%A0%90/place/12764501?c=14144625.1949741,4515071.1475169,14,0,0,0,dh')
.then(html => {
    let ulList = [];
    const $ = cheerio.load(html); 
    const $bodyList = $("div.link_search");
    console.log($bodyList);

    $bodyList.each(function(i, elem){
        ulList[i] = {
            url: $(this).find('a.thumb_box.ng-star-inserted').attr('src')

        };
    });

    // const $foodList = $('div.list_area ul.list_place_col1');
    // console.log($foodList.html());

    const data = ulList.filter(n => n.url);
    console.log(data);
    return data;
})