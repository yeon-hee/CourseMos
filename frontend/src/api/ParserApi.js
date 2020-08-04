import axios from 'axios';
import convert from 'xml-js';
var url = 'http://apis.data.go.kr/B553077/api/open/sdsc/storeListInRadius?radius=300&cx=126.573301&cy=33.449826&ServiceKey=';
var serviceKey = 'T4t8oOI6faoEB54PfjShnIR%2BVN3uMc2nqdKyC0Dn%2BTvNl4Px5%2FoFq4ECOioJeq5H25bdPnjFwahyMAX3Qk4y1A%3D%3D';
const RemoveJsonTextAttribute = (value, parentElement) => {
    try {
        const keyNo = Object.keys(parentElement._parent).length;
        const keyName = Object.keys(parentElement._parent)[keyNo - 1];
        parentElement._parent[keyName] = value;
    } catch (e) {
        console.log(e);
    }
};
const options = { compact: true, spaces: 4, textFn: RemoveJsonTextAttribute };
axios.get(url+serviceKey)
    .then(res => {
        // console.dir(res.data);
        var json = convert.xml2json(res.data, options);
        // console.dir(json);
        var obj = JSON.parse(json);
        console.dir(obj.response.body);
        let num = obj.response.body.totalCount;
        for(let i=0; i<num; i++) {
            console.dir(obj.response.body.items.item[i])
        }
    })
    .catch(res => {
        console.dir(res);
    });



