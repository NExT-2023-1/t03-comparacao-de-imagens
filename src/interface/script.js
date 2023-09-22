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

/*imageInput2.addEventListener('change', () => {
    loadImage(imageInput2, image2Element);
});*/

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


async function handlePostImage(event) {
    event.preventDefault();
    

    const file = imageInput1.files[0]; 
    if (!file) {
        console.error('Nenhuma imagem selecionada.');
        return;
    }

    const formData = new FormData();
    formData.append("image", file);

    try {
        const data = await fetch("http://localhost:8080/image", {
            mode: "no-cors",
            method: "POST",
            body: formData,
            headers: {
                'Access-Control-Allow-Origin': "http://127.0.0.1:5500/",
            }
        });

        if (data.ok) {
            const response = await response.json();
            document.getElementById('form').textContent = JSON.stringify(response, null, 2);
      
        }
        console.log(...formData)
        console.log(data)
    } catch (error) {
        console.log("Erro", error)
    }
}