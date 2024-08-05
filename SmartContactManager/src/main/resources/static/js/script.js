console.log("this is script file");

const toggleSidebar=()=>{
	
	if($(".sidebar").is(":visible")){
		$(".sidebar").css("display","none");
		$(".content").css("margin-left","5%");
		
	}
	else{
		$(".sidebar").css("display","block");
		$(".content").css("margin-left","20%");
	}
};



const search=()=>{


let query=$("#search-input").val();

if(query == ""){
	$(".search-result").hide();
}
else{
console.log(query);
// sending request to server

let url=`http://localhost:8080/search/${query}`;

fetch(url).then((response)=>
{
return response.json();
}).then((data)=>{
// data ......
console.log(data);


let text=`<div class='list-group' >`;


data.forEach((contacts)=>{
	text += `<a href="/user/${contacts.CId}/contact" class='list-group-item list-group-item-action'> ${contacts.name} </a>`;
});
text += `</div>`;
$(".search-result").html(text);
$(".serach-result").show();



});
$(".search-result").show();
}



};

