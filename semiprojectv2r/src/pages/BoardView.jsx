import React, {useEffect, useState, useRef} from "react";
import {useParams} from "react-router-dom";
import "../styles/board.css"

const BoardView = () => {
    const [boardData, setBoardData] = useState({});
    const params = useParams();
    const bno = params.bno;

    const fetchURL = `http://localhost:8080/api/board/view/${bno}`;

    // react에서 부수작업side effect을 수행하기 위한 hook
    // 부수작업 : 데이터 가져오기, DOM 조작, 로그
    useEffect(() => {
        fetch(fetchURL, {
            headers: { 'Accept': 'application/json' }
        }).then(res => res.json())
            .then(data => {
                console.log(data);
                setBoardData(data);
            })
            .catch(err => console.log('오류발생!! ', err));
    }, []);

    return (
        <>
            <main id="content">
                <h2>게시판 본문 글</h2>
                <div className="row offset-1 col-10 my-3">
                    <table className="">
                        <thead>
                        <tr>
                            <td>
                                <button type="button" className="btn btn-light">이전게시물</button>
                                &nbsp;
                                <button type="button" className="btn btn-light">다음게시물</button>
                            </td>
                            <td className="text-end">
                                <button type="button" className="btn btn-primary col-3" id="newbdbtn">
                                    새글쓰기
                                </button>
                            </td>
                        </tr>
                        </thead>
                        <tbody>
                        {
                            (!boardData.bd) ?
                                <tr>
                                    <td colSpan="3">본문글이 없어요</td>
                                </tr>
                                :
                                <>
                                    <tr>
                                        <th className="vtit" colSpan="2">{boardData.bd.title}</th>
                                    </tr>
                                    <tr className="vinfo">
                                        <td>{boardData.bd.userid}</td>
                                        <td className="text-end vinfo">{boardData.bd.regdate} / {boardData.bd.thumbs} / {boardData.bd.views}</td>
                                    </tr>
                                    <tr>
                                        <td className="vcont pre" colSpan="2">{boardData.bd.contents}</td>
                                    </tr>
                                </>
                        }
                        </tbody>
                        <tfoot>
                        <tr>
                            <td>
                                <button type="button" className="btn btn-warning">
                                    수정하기
                                </button>
                                &nbsp;
                                <button type="button" className="btn btn-danger" id="rmvbdbtn">
                                    삭제하기
                                </button>
                            </td>
                            <td className="text-end">
                                <button type="button" className="btn btn-light" id="lstbdbtn">
                                    목록으로
                                </button>
                            </td>
                        </tr>
                        </tfoot>
                    </table>

                    <div className="my-3">
                        <h3><i className="fa fa-commenting">나도 한 마디</i></h3>
                    </div>
                    <table className="table reply-border" data-loginuser="">
                        <form className="card card-body bg-light" method="post"
                              name="replyfrm" id="replyfrm">
                            <div className="row align-items-center">
                                <div className="col text-center"><label>abc123</label></div>

                                <div className="col-8"><textarea id="comments" rows="10" name="comments"
                                                                 className="form-control col-7"></textarea></div>

                                <div className="col">
                                    <button type="button" id="replybtn"
                                            className="btn btn-dark">
                                        <i className="fa fa-commenting"></i>댓글쓰기
                                    </button>
                                </div>
                            </div>
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                            <input type="hidden" name="userid" value="abc123"/>
                            <input type="hidden" name="pno" value={bno}/>
                        </form>
                        <tbody>
                        {
                            (boardData.rps) && boardData.rps.map(rp => (
                                (rp.rno === rp.ref) ?
                                    <>
                                        (
                                        <tr key={`reply-${rp.rno}`}>
                                            <td className="text-left">{rp.userid}</td>
                                            <td>
                                                <div className="cmtbg"><span>{rp.regdate}</span>
                                                    <span className="pushend"><a href="#" data-ref="${rp.rno}"
                                                                                 data-bs-toggle="modal"
                                                                                 data-bs-target="#cmtModal">[추가]</a> [수정] [삭제]</span>
                                                </div>
                                                <p className="py-1 pre">{rp.comments}</p></td>
                                        </tr>
                                        )
                                    </>
                                    :
                                    (<tr key={`reply-${rp.rno}`}>
                                        <td className="text-left">&nbsp;&nbsp;</td>
                                        <td>
                                            <div className="rpybg"><span>{rp.userid}</span>
                                                <span className=" pushend">{rp.regdate}</span></div>
                                            <p className="py-1 pre">{rp.comments}</p>
                                        </td>
                                    </tr>)
                            ))
                        }
                        </tbody>
                    </table>
                </div>
                <div className="modal hide" id="cmtModal" role="dialog">
                    <div className="modal-dialog" role="document">
                        <div className="modal-content">
                            <div className="modal-header">
                                <h3 className="modal-title">대댓글 쓰기</h3>
                            </div>
                            <div className="modal-body">
                                <form name="cmmtfrm" id="cmmtfrm" method="post">
                            <textarea name="comments" id="cmmt" rows="8" cols="60"
                                      className="span4"></textarea>
                                    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                                    <input type="hidden" name="userid" value="abc123"/>
                                    <input type="hidden" name="pno" value={bno}/>
                                    <input type="hidden" name="ref" id="refno"/>
                                </form>
                            </div>
                            <div className="modal-footer justify-content-center">
                                <button type="button" id="cmmtbtn"
                                        className="btn btn-danger">대댓글 작성
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </main>
        </>
    );
}

export default BoardView;