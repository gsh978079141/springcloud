export async function getTables() {
    return [
        {
            snCode: "RACK000001",
            cameras: [
                {
                    location: "front",
                    videoUrl: "rtsp://admin:123456@192.168.41.148:554/stream0",
                },
                {
                    location: "top",
                    videoUrl: "rtsp://admin:123456@192.168.41.228:554/stream0",
                },
                {
                    location: "side",
                    videoUrl: "rtsp://admin:123456@192.168.41.2:554/stream0",
                },
            ],
        },
        {
            snCode: "RACK000002",
            cameras: [
                {
                    location: "front",
                    videoUrl: "rtsp://admin:123456@192.168.41.87:554/stream0",
                },
                {
                    location: "top",
                    videoUrl: "rtsp://admin:123456@192.168.41.69:554/stream0",
                },
                {
                    location: "side",
                    videoUrl: "rtsp://admin:123456@192.168.41.79:554/stream0",
                },
            ],
        },
        {
            snCode: "RACK000003",
            cameras: [
                {
                    location: "front",
                    videoUrl: "rtsp://admin:123456@192.168.41.193:554/stream0",
                },
                {
                    location: "top",
                    videoUrl: "rtsp://admin:123456@192.168.41.99:554/stream0",
                },
                {
                    location: "side",
                    videoUrl: "rtsp://admin:123456@192.168.41.71:554/stream0",
                },
            ],
        },
        {
            snCode: "RACK000004",
            cameras: [
                {
                    location: "front",
                    videoUrl: "rtsp://admin:123456@192.168.41.200:554/stream0",
                },
                {
                    location: "top",
                    videoUrl: "rtsp://admin:123456@192.168.41.150:554/stream0",
                },
                {
                    location: "side",
                    videoUrl: "rtsp://admin:123456@192.168.41.131:554/stream0",
                },
            ],
        },
    ];

}