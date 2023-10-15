function upload_file(e) {
    e.preventDefault();
    ajax_file_upload(e.dataTransfer.files);
  }
  
  function file_explorer() {
    document.getElementById('selectfile').click();
    document.getElementById('selectfile').onchange = function() {
      ajax_file_upload(document.getElementById('selectfile').files);
    };
  }
  
  function ajax_file_upload(files_obj) {
    if(files_obj != undefined) {
        var ul = document.getElementById("check-list");
      // Преобразуем объект FileList в массив объектов
      for (var i = 0; i < files_obj.length; i++) {
        var li = document.createElement("li");
        li.appendChild(document.createTextNode(files_obj[i].name));
        ul.appendChild(li);
      }
  
    //   var data = {
    //     files: filesArray,
    //     text: document.getElementById("input-prompt").value
    //   };
  
    //   var jsonData = JSON.stringify(data);
    //   console.log(jsonData);
    }
  }