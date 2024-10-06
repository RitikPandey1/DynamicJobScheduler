var obj = {};

function init() {
  console.log("Hello!");
  console.log(tasks);
}

init();

function selectTask() {
  var e = document.getElementById("taskDropDown");
  console.log(e.value);
  obj = tasks[e.value];
  console.log(obj);

  var element = document.getElementById("input-container");

  while (element.hasChildNodes()) {
    element.removeChild(element.lastChild);
  }

  obj['paramObject'] = {};

  for (var i = 0; i < obj.params.length; i++) {
    element.appendChild(
      document.createTextNode(
        obj.params[i].name + "(Type : " + obj.params[i].type + ")"
      )
    );
    // Create an <input> element, set its type and name attributes
    element.appendChild(document.createElement("br"));
    var input = document.createElement("input");
    input.type = "text";
    input.name = obj.params[i].name;
    input.addEventListener("change", function (e) {
      console.log(e.target.value);
      console.log(e.target.name);
      obj['paramObject'][e.target.name] = e.target.value;
    });
    element.appendChild(input);
    // Append a line break
    element.appendChild(document.createElement("br"));
  }

  element.appendChild(document.createTextNode("Cron Expression"));
  element.appendChild(document.createElement("br"));
  var cronInput = document.createElement("input");
  cronInput.type = "text";
  cronInput.name = "cronExp";
  cronInput.addEventListener("change",function(e){
    console.log(e.target.value);
    console.log(e.target.name);
    obj['cronExp'] = e.target.value;
  })
  element.appendChild(cronInput);
  element.appendChild(document.createElement("br"));
}

async function  submitJob(){
  obj["paramObject"] =  JSON.stringify(obj["paramObject"])
  console.log(obj);

  var response = await axios.post("http://localhost:8080/invoke-task",obj);
  console.log(response); 

}