
const imageInput1 = document.getElementById('image-input1');
const imageInput2 = document.getElementById('image-input2');
const image1Element = document.getElementById('image1');
const image2Element = document.getElementById('image2');
const resultText = document.getElementById('result');
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


// imageInput2.addEventListener('change', () => {
//     loadImage(imageInput2, image2Element);
// });


function compareImages() {
    if (!image1Element.src || !image2Element.src) {
        resultText.textContent = 'Carregue as duas imagens para comparar.';
        return;
    }


    if (image1Element.src === image2Element.src) {
        resultText.textContent = 'As duas imagens são iguais.';
    } else {
        resultText.textContent = 'As duas imagens são diferentes.';
    }
}

// função para tentar fazer o post da imagem no backend *não está funcionando*
// dizendo que o arquivo não é do tipo multipart
async function handlePostImage(event) {
    event.preventDefault()
    for (const file of imageInput1.files) {
        formData.append("image", file);
    }

    try {
        const data = await fetch("http://localhost:8080/image", {
            method: "POST",
            headers: {
                'Access-Control-Allow-Origin': "http://127.0.0.1:5500/",
            },
            body: {
                image: formData
            }
        })
        const response = await data.json()


        console.log(...formData)
        console.log(data)
    } catch (error) {
        console.log("Erro", error)
    }
}


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

window.onload = async () => {
    await handleGetImage("nodejs_logo.png")
}



