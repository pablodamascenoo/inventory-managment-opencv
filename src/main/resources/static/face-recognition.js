let video = document.getElementById('input_video');
let canvas = document.getElementById('output_video');
let ctx = canvas.getContext('2d');
let streaming = false;
let faceCascade;

async function playVideo() {
    const mediaStream = await navigator.mediaDevices.getUserMedia({ video: true, audio: false });
    if ("srcObject" in video) {
        video.srcObject = mediaStream;
    } else {
        video.src = URL.createObjectURL(mediaStream);
    }
    video.onloadedmetadata = () => {
        video.play();
        streaming = true;
        processVideo()
    };
};

async function loadCascade() {
    return new Promise((resolve, reject) => {
        let xhr = new XMLHttpRequest();
        xhr.open('GET', '/haarcascade_frontalface_default.xml', true);
        xhr.responseType = 'arraybuffer';
        xhr.onload = function () {
            if (this.status === 200) {
                let data = new Uint8Array(this.response);
                resolve(data);
            } else {
                reject(new Error('Failed to load cascade file'));
            }
        };
        xhr.onerror = function () {
            reject(new Error('Network error'));
        };
        xhr.send();
    });
}

async function onCVLoad() {
    if (cv.getBuildInformation) {
        let data = await loadCascade();
        cv.FS_createDataFile('/', 'haarcascade_frontalface_default.xml', data, true, false, false);
        faceCascade = new cv.CascadeClassifier();
        faceCascade.load('haarcascade_frontalface_default.xml');
        playVideo();
    } else {
        if (cv instanceof Promise) {
            cv = await cv;
            let data = await loadCascade();
            cv.FS_createDataFile('/', 'haarcascade_frontalface_default.xml', data, true, false, false);
            faceCascade = new cv.CascadeClassifier();
            faceCascade.load('haarcascade_frontalface_default.xml');
            playVideo();
        } else {
            cv['onRuntimeInitialized'] = async () => {
                let data = await loadCascade();
                cv.FS_createDataFile('/', 'haarcascade_frontalface_default.xml', data, true, false, false);
                faceCascade = new cv.CascadeClassifier();
                faceCascade.load('haarcascade_frontalface_default.xml');
                playVideo();
            }
        }
    }
}

function processVideo() {
    if (!streaming) return;
    let frame = new cv.Mat(video.height, video.width, cv.CV_8UC4);
    let gray = new cv.Mat();
    ctx.drawImage(video, 0, 0, canvas.width, canvas.height);
    frame.data.set(ctx.getImageData(0, 0, canvas.width, canvas.height).data);
    cv.cvtColor(frame, gray, cv.COLOR_RGBA2GRAY, 0);
    let faces = new cv.RectVector();
    faceCascade.detectMultiScale(gray, faces, 1.1, 3, 0);
    for (let i = 0; i < faces.size(); ++i) {
        let face = faces.get(i);
        ctx.strokeStyle = 'red';
        ctx.lineWidth = 2;
        ctx.strokeRect(face.x, face.y, face.width, face.height);
    }
    frame.delete();
    gray.delete();
    faces.delete();
    requestAnimationFrame(processVideo);
}