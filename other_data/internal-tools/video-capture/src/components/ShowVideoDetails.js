import {Component} from "react";
import {Button, Card, Flex, InputItem, SegmentedControl, Toast, WhiteSpace, WingBlank,} from "antd-mobile";
import {endRecord, getCurrentTask, startRecord} from "../services/recording";
import tables from "../data/tables";

const isIPhone = new RegExp("\\biPhone\\b|\\biPod\\b", "i").test(
    window.navigator.userAgent
);
let moneyKeyboardWrapProps;
if (isIPhone) {
    moneyKeyboardWrapProps = {
        onTouchStart: (e) => e.preventDefault(),
    };
}

export default class ShowVideoDetails extends Component {
    constructor(props) {
        super(props);
        this.state = {
            curIdx: 0,
            current: tables[0],
            isRecording: false,
            startTime: 0,
            recordingResult: {
                recordingId: 0,
                results: [],
            },
        };
    }

    loadCurrentTask = async () => {
        const curSnCode = this.state.current.snCode;
        await getCurrentTask(curSnCode);
    };

    onValueChange = async (value) => {
        let curIdx = 0;
        let table = tables[0];
        for (let i = 0; i < tables.length; i++) {
            if (tables[i].snCode === value) {
                curIdx = i;
                table = tables[i];
            }
        }
        this.setState({
            current: table,
            curIdx,
        });
        let data = await getCurrentTask(value);
        if (data.task) {
            const {task} = data;
            this.setState({
                isRecording: true,
                startTime: task.startTime,
            });
        } else {
            this.setState({
                isRecording: false,
                startTime: 0,
            });
        }
    };

    startRecording = async () => {
        const {current, filePath} = this.state;
        Toast.loading(`正在启动摄像头录制，请稍等...`, 0);
        const data = await startRecord(current, filePath);
        const {success, message} = data;
        if (success) {
            this.setState({
                ...this.setState,
                isRecording: true,
                recordingResult: [],
            });
            Toast.hide();
            Toast.success(`启动录制成功！`, 1);
        } else {
            Toast.hide();
            Toast.fail(`启动录制失败: ${message}`, 3);
        }
    };

    finishRecording = async () => {
        const {current} = this.state;
        Toast.loading(`结束摄像头录制中...`, 0);
        const data = await endRecord(current);
        const {success, code, recordingId, results} = data;
        if (success) {
            this.setState({
                ...this.setState,
                isRecording: false,
                recordingResult: {
                    recordingId,
                    results,
                },
            });
            Toast.hide();
            Toast.success("结束录制成功！", 1);
        } else {
            Toast.hide();
            Toast.fail(`结束录制失败: ${code}`, 1);
        }
    };

    async componentDidMount() {
        this.setState({
            tables,
            current: tables[0],
            curIdx: 0,
        });
        Toast.loading("loading...");
        let data = await getCurrentTask(tables[0].snCode);
        if (data.task) {
            const {task} = data;
            this.setState({
                ...this.state,
                isRecording: true,
                startTime: task.startTime,
                filePath: "",
            });
        }
        Toast.hide();
    }

    render() {
        const snCodes = [];
        console.log(`${JSON.stringify(tables)}`);
        for (let table of tables) {
            snCodes.push(table.snCode);
        }
        const {
            current,
            isRecording,
            curIdx,
            recordingResult,
        } = this.state;
        return (
            < WingBlank
        size = "lg"
        className = "sc-example" >
            < SegmentedControl
        onValueChange = {this.onValueChange}
        style = {
        {
            marginTop: "20px"
        }
    }
        selectedIndex = {curIdx}
        values = {snCodes}
        />
        < div
        style = {
        {
            padding: "50px 0"
        }
    }>
        {
            current &&
            current.cameras.map((i, index) => (
                < div
            style = {
            {
                display: "flex",
                    justifyContent
            :
                "space-around",
                    height
            :
                "50px",
            }
        }
            key = {index}
                >
                < span > {i.location}【{
            i.locationDesc
        }】<
            /span>
            < span > {i.videoUrl} < /span>
            < /div>
        ))
        }
    <
        /div>
        < InputItem
        placeholder = "请输入"
        onChange = {(v)
    =>
        {
            this.setState({
                filePath: v,
            });
        }
    }
        moneyKeyboardWrapProps = {moneyKeyboardWrapProps}
            >
            文件路径
            < /InputItem>
            < Flex
        style = {
        {
            marginTop: "20px"
        }
    }>
    <
        Flex.Item >
        < Button
        onClick = {this.startRecording}
        type = "primary"
        disabled = {isRecording}
            >
            开始录制
            < /Button>
            < /Flex.Item>
            < Flex.Item >
            < Button
        onClick = {this.finishRecording}
        disabled = {
        !isRecording
    }
        type = "primary"
            >
            结束录制
            < /Button>
            < /Flex.Item>
            < /Flex>
            < WhiteSpace
        size = "lg" / >
            < Card >
            < Card.Header
        title = "录制结果" / >
            < Card.Body >
            {/* <div>视频当前存放路径:{filePath}</div> */}
        {
            recordingResult.results &&
            recordingResult.results.map((result) => {
                return (
                    < div >
                    < div > 摄像头位置
            :
                {
                    result.cameraId
                }
            <
                /div>
                < div
                style = {
                {
                    width: "100%"
                }
            }>
            <
                video
                src = {result.accessUrl}
                width = "100%"
                controls
                > < /video>
                < /div>
                < /div>
            )
                ;
            })
        }
    <
        /Card.Body>
        < /Card>
        < /WingBlank>
    )
        ;
    }
}
