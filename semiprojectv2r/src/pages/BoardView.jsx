import React, {useEffect, useState, useRef} from "react";
import {useParams} from "react-router-dom";
import "../styles/board.css"

const BoardView = () => {
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
                        <tbody id="boardView">

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
                </div>
            </main>
        </>
);
}

export default BoardView;