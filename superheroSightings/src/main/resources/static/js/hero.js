$(document).ready(function ()
{
    $('#heroImage').change(function ()
    {
        let imageThumbnail = $('#imageThumbnail');
        showImageThumbnail(this, imageThumbnail);
    })
    $('#editHeroImage').change(function ()
    {
        let editImageThumbnail = $('#editImageThumbnail');
        showImageThumbnail(this, editImageThumbnail);

    });
});

function  showImageThumbnail(fileInput, imageThumbnail)
{
    //alert("showImageThumbnail");
    const [file] = fileInput.files;
    if (file)
    {
        imageThumbnail.attr('src', URL.createObjectURL(file));
    }
    ;
}

//function showThumbnail(filename)
//{
//    alert("showThumbnail" + filename);
//    let filepath = "./src/main/resources/static/hero-images/" + filename;
//    alert(filepath);
//    file = new File(filepath);
//    $('#editImageThumbnail').attr('src',URL.createObjectURL(file));   
////    let editImageThumbnail = $('#editImageThumbnail');
////    showImageThumbnail(this, editImageThumbnail);
//}

