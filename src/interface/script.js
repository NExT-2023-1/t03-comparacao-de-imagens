const imageInput1 = document.getElementById('image-input1');
const imageInput2 = document.getElementById('image-input2');
const image1Element = document.getElementById('image1');
const image2Element = document.getElementById('image2');
const resultText = document.getElementById('result');
const form = document.getElementById('form');
const formData = new FormData();


function loadImage(input, imageElement) {
    const file = input.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = (e) => {
            imageElement.src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
}

imageInput1.addEventListener('change', () => {
    loadImage(imageInput1, image1Element);
});

imageInput2.addEventListener('change', () => {
    loadImage(imageInput2, image2Element);
});


// função para comparar imagens
async function compareImages() {
    if (!imageInput1.files) {
        return alert("Adicione a primeira imagem")
    } 

    if (!imageInput2.files) {
        return alert("Adicione a segunda imagem")
    }

    formData.append("image1", imageInput1.files[0])
    formData.append("image2", imageInput2.files[0])

    try {
    const data = await fetch("http://localhost:8080/image/compareTwoImages", {
            method: "POST",
            headers: {
                'Access-Control-Allow-Origin': "http://127.0.0.1:5500/",
            },
            body: formData
        })
        
        const response = await data.json()
        alert(response.score)
    } catch (error) {
        console.log("Erro", error)
    }

}

form.addEventListener("submit", async function (event) {
    //impede que a tela recarrege mas com a chamada async não está funcionando
    event.preventDefault();
    setTimeout(async () => {
        await compareImages();
    }, 1000)
})

// função para fazer o post da imagem no backend 
async function handlePostImage(event) {
    event.preventDefault()
    try {
        formData.append("image", imageInput1.files[0]);
        
        const data = await fetch("http://localhost:8080/image", {
            method: "POST",
            headers: {
                'Access-Control-Allow-Origin': "http://127.0.0.1:5500/",
            },
            body: formData
        })
        
        console.log(...formData)
        console.log(data)
    } catch (error) {
        console.log("Erro", error)
    }
}

// função de get de uma única imagem
async function handleGetImage(image) {
    try {
        const data = await fetch(`http://localhost:8080/image/${image}`, {
            method: "GET",
            headers: {
                'Access-Control-Allow-Origin': 'http://127.0.0.1:5500/'
            }
        })

        console.log(data)
    } catch (error) {
        console.log(error)        
    }
}





