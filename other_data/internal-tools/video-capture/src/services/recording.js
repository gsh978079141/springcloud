import request from "../utils/request";
import moment from "moment";

export async function startRecord(tableInfo, filePath) {
    const newPath = filePath === '' ? '' : `/${filePath}`;
    const {snCode, cameras} = tableInfo;
    let payload = {
        recordingId: snCode,
        dirPath: `videos/material/${snCode}${newPath}/${moment().format("YYYYMMDD_HHmmss")}/`,
        sources: cameras.map((i) => {
            return {
                cameraId: `${snCode}-${i.location}`,
                videoUrl: i.videoUrl,
                fileName: `${i.location}.mp4`,
            };
        })
    };
    const data = await request('/recording/start', {
        method: "POST",
        body: payload,
    });
    console.log(`end record: ${JSON.stringify(data)} `);
    return data;
}

export async function endRecord(tableInfo) {
    const {snCode} = tableInfo;
    const resp = await request(`/recording/finish`, {
        method: "POST",
        body: {recordingId: snCode},
    });
    console.log(`end record: ${JSON.stringify(resp)} `)
    return resp;
}


export async function getCurrentTask(recordingId) {
    const resp = await request(`/recording/current_tasks/${recordingId}`, {
        method: "GET",
    });
    console.log(`getCurrentTask: ${JSON.stringify(resp)} `)
    return resp;
}